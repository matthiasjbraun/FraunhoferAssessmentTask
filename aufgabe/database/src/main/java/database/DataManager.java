package database;

import jakarta.persistence.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Iterator;
import java.util.List;

public class DataManager {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Fraunhofer_PU");
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public DataManager() {
        createValidator();
    }

    private EntityManager createEntityManager() {
        return emf.createEntityManager();
    }
    private void createValidator() {
        Validator validator = factory.getValidator();
        validator.validate(Person.class);
    }
    /**
     * persists a Person
     * @param person Person Entity
     */
    public void persistPerson(Person person) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } catch (PersistenceException pe) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

    /**
     * persists a List of Persons
     * @param personList List of Person Entities
     */
    public void persistPerson(List<Person> personList) {
        EntityManager em = createEntityManager();
        Iterator<Person> it = personList.iterator();
        try {
            em.getTransaction().begin();
            while (it.hasNext()) {
                em.persist(it.next());
            }
            em.getTransaction().commit();
        } catch (PersistenceException pe) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param id id of the Person
     * @return List of Person
     */
    public Person findPersonWithId(String id) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Person p WHERE p.id = ?1", Person.class).setParameter(1, id).getSingleResult();
    }

    public List<Person> findPersonWithName(String name) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Person p WHERE p.name = ?1", Person.class).setParameter(1, name).getResultList();
    }

    public List<Person> findPersonWithAll(String id, String name) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Person p WHERE p.id = ?1 OR p.name = ?2", Person.class)
                .setParameter(1, id)
                .setParameter(2, name)
                .getResultList();
    }
}
