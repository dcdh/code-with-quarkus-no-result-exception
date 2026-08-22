package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional//(dontRollbackOn = NoResultException.class)
public class MyEntityRepository {

    @Inject
    EntityManager entityManager;

    @Inject
    TransactionManager transactionManager;

    List<Integer> transactionStatus = new ArrayList<>();

    public void callFailNoResultException() throws SystemException {
        transactionStatus.add(transactionManager.getStatus());
        try {
            getBy();
        } catch (final NoResultException exception) {
            transactionStatus.add(transactionManager.getStatus());
        }
        transactionStatus.add(transactionManager.getStatus());
    }

    // can be called internally by multiple other methods and implementors of MyEntityRepository
    protected MyEntity getBy() {
        // this following exception can be thrown when calling getSingleResult()
//            return entityManager.createNamedQuery("MyEntity.getBy", MyEntity.class)
//                    .getSingleResult();
        throw new NoResultException();
    }

    public List<Integer> getTransactionStatus() {
        return transactionStatus;
    }
}
