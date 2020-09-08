package org.codejudge.sb;

import org.codejudge.sb.rating.LocalTransactionId;
import org.codejudge.sb.rating.ResourceLockManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.function.Consumer;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LocalTransactionId.class)
public class ResourceLockManagerTest {

    private ResourceLockManager<Integer> resourceLockManager;

    private LocalTransactionId transactionId;

    private LocalTransactionId transactionId2;

    @Mock
    private Consumer<Integer> callback;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        resourceLockManager = new ResourceLockManager<>();
        transactionId = PowerMockito.mock(LocalTransactionId.class);
        transactionId2 = PowerMockito.mock(LocalTransactionId.class);
        PowerMockito.when(transactionId.getId()).thenReturn(12L);
    }

    @Test
    public void shouldFail_WhenNoResourceIdInStateMap() {
        exceptionRule.expect(IllegalStateException.class);
        resourceLockManager.release(1, transactionId);
    }

    @Test
    public void shouldPass_WhenTransactionIdHasAConsumerAttachedToResourceAndIsOwner() {
        resourceLockManager.lock(1, transactionId, callback);
        resourceLockManager.lock(1, transactionId2, callback);
        resourceLockManager.lock(1, transactionId2, callback);
        resourceLockManager.release(1, transactionId);
    }

    @Test
    public void shouldPass_WhenTransactionIdHasAConsumerAndIsOwner() {
        resourceLockManager.lock(1, transactionId, callback);
        resourceLockManager.lock(1, transactionId, callback);
        resourceLockManager.release(1, transactionId);
    }

    @Test
    public void shouldPass_WhenTransactionIdHasAConsumerAttachedToResourceAndIsConsumer() {
        resourceLockManager.lock(1, transactionId, callback);
        resourceLockManager.lock(1, transactionId2, callback);
        resourceLockManager.release(1, transactionId2);
    }

    @Test
    public void shouldPass_WhenTransactionIdHasAOwnerAndConsumerAttachedToResourceAndBothAccessResource() {
        resourceLockManager.lock(1, transactionId, callback);
        resourceLockManager.lock(1, transactionId2, callback);
        resourceLockManager.release(1, transactionId);
        resourceLockManager.release(1, transactionId2);
    }
}
