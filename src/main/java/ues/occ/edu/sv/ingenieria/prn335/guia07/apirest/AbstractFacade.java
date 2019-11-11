/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.prn335.guia07.apirest;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author christian
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();
    
    
    
     /**
     *
     * @param entity parametro a ser persistido
     * @return la entidad creada
     */
    public T crear(T entity) {
        EntityManager em = getEntityManager();
        try {
            if (em != null && entity != null) {
                em.persist(entity);
                return entity;
            } else {
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
        return null;
    }

    /**
     *
     * @param entity parametro a editar
     * @return la entidad modificada
     */
    public T editar(T entity) {
        EntityManager em = getEntityManager();
        try {
            if (em != null && entity != null) {
                em.merge(entity);
                return entity;
            } else {
                System.out.println("algo es nulo");
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }

        return null;
    }

    /**
     * 
     * @param entity entidad a remover de los registros
     * @return 
     */
    public T remover(T entity) {
        EntityManager em = getEntityManager();
        try {
            if (em != null && entity != null) {
                em.remove(getEntityManager().merge(entity));
                return entity;
            } else {
                System.out.println("algo es nulo");
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
        return null;
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int init, int end) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query query = getEntityManager().createQuery(cq);
        query.setFirstResult(init);
        query.setMaxResults(end);

        return query.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
