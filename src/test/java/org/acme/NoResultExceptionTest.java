package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Status;
import jakarta.transaction.SystemException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class NoResultExceptionTest {

    @Inject
    MyEntityRepository myEntityRepository;

    @Test
    void shouldNotMarkTransactionAsRollbackWhenNoResultExceptionIsThrown() throws SystemException {
        myEntityRepository.callFailNoResultException();

        // Status.STATUS_ACTIVE = 0
        // Status.STATUS_MARKED_ROLLBACK = 1
        assertThat(myEntityRepository.getTransactionStatus()).containsExactly(Status.STATUS_ACTIVE,
                Status.STATUS_ACTIVE,
                Status.STATUS_ACTIVE);
    }
}
