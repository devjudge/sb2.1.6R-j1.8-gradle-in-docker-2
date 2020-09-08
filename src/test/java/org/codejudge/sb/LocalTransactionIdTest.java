package org.codejudge.sb;

import org.codejudge.sb.rating.InvalidStateException;
import org.codejudge.sb.rating.LocalTransactionId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LocalTransactionId.class)
public class LocalTransactionIdTest {

    private LocalTransactionId transactionId;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        PowerMockito.mock(LocalTransactionId.class);
        transactionId = LocalTransactionId.create("12");
    }

    @Test
    public void shouldFail_WhenTransactionIdIsAString() {
        exceptionRule.expect(InvalidStateException.class);
        LocalTransactionId.create("Hello");
    }

    @Test
    public void shouldPass_WhenSameStringIsPassedTwice() {
        LocalTransactionId transaction2 = LocalTransactionId.create("12");
        Assert.assertTrue(transactionId.equals(transactionId));
        Assert.assertTrue(transactionId.equals(transaction2));
    }

    @Test
    public void shouldPass_WhenIdIsReturned() {
        long id = transactionId.getId();
        Assert.assertEquals(12L, id);
    }

    @Test
    public void shouldPass_WhenHashCodeCalledOnSameIdObjects() {
        LocalTransactionId transaction2 = LocalTransactionId.create("12");
        Assert.assertEquals(transactionId.hashCode(), transaction2.hashCode());
    }

    @Test
    public void shouldFail_WhenHashCodeCalledOnSameIdObjects() {
        Integer randomNumber = null;
        Assert.assertFalse(transactionId.equals(randomNumber));
    }
}
