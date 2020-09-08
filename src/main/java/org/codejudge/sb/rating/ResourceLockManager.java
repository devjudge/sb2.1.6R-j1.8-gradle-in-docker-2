package org.codejudge.sb.rating;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author jsantos
 */
public class ResourceLockManager<T> {

    private final HashMap<T, ResourceData> resourceStateMap = new HashMap<>();
    private final HashMap<LocalTransactionId, TransactionLocks> transactionLocks = new HashMap<>();
    private final HashMap<LocalTransactionId, List<T>> transactionConsumers = new HashMap<>();

    public void lock(final T resourceId, final LocalTransactionId transactionId, final Consumer<T> callback) {
        if (!resourceStateMap.containsKey(resourceId)) {
            resourceStateMap.put(resourceId, new ResourceData());
        }

        final ResourceData data = resourceStateMap.get(resourceId);
        if (data.owner == null || data.owner.equals(transactionId)) {
            if (data.owner == null) {
                data.owner = transactionId;
                transactionLocks.compute(transactionId, (k, v) -> v == null ? new TransactionLocks() : v)
                        .registerResource(resourceId);
            }

            callback.accept(resourceId);
        } else {
            data.consumers.add(new ConsumerData<>(callback, transactionId));
            transactionConsumers
                    .computeIfAbsent(transactionId, tx -> new ArrayList<>())
                    .add(resourceId);
        }
    }

    public void release(final T resourceId, final LocalTransactionId transactionId) {
        if (!resourceStateMap.containsKey(resourceId)) {
            throw new IllegalStateException(String.format("transaction %s does not hold a lock on resource %s",
                    transactionId, resourceId));
        }

        if (transactionConsumers.containsKey(transactionId)) {
            transactionConsumers.get(transactionId).remove(resourceId);
            if (resourceStateMap.containsKey(resourceId)) {
                resourceStateMap.get(resourceId).consumers.remove(new ConsumerData<>(null, transactionId));
            }

            if (transactionConsumers.get(transactionId).isEmpty()) {
                transactionConsumers.remove(transactionId);
            }
        }

        if (transactionLocks.containsKey(transactionId)) {
            transactionLocks.get(transactionId).unregisterResource(resourceId);
            doRelease(resourceId);
        }
    }



    private void doRelease(final T resourceId) {
        final ResourceData data = resourceStateMap.get(resourceId);
        if (!data.consumers.isEmpty()) {
            final ConsumerData consumer = data.consumers.remove(0);
            transactionConsumers.getOrDefault(consumer.transactionId, Collections.emptyList()).remove(resourceId);
            if (transactionConsumers.containsKey(consumer.transactionId)) {
                if (transactionConsumers.get(consumer.transactionId).isEmpty()) {
                    transactionConsumers.remove(consumer.transactionId);
                }
            }

            data.owner = consumer.transactionId;
            transactionLocks.compute(consumer.transactionId, (k, v) -> v == null ? new TransactionLocks() : v)
                    .registerResource(resourceId);

            consumer.consumer.accept(resourceId);
        } else {
            resourceStateMap.remove(resourceId);
        }
    }

    private class ResourceData {

        private final ArrayList<ConsumerData> consumers = new ArrayList<>();

        private LocalTransactionId owner;
    }

    public static class ConsumerData<T> {

        private final Consumer<T> consumer;
        private final LocalTransactionId transactionId;

        ConsumerData(final Consumer<T> consumer, final LocalTransactionId transactionId) {
            this.consumer = consumer;
            this.transactionId = transactionId;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ConsumerData)) {
                return false;
            }
            final ConsumerData that = (ConsumerData) o;
            return Objects.equals(transactionId, that.transactionId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(transactionId);
        }
    }

    private class TransactionLocks {

        private final HashSet<T> resources = new HashSet<>();

        public void registerResource(final T resource) {
            resources.add(resource);
        }

        public void unregisterResource(final T resource) {
            resources.remove(resource);
        }
    }
}
