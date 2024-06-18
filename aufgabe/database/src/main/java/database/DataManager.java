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
        //validator.validate(Person.class);
    }

    /**
     * persists a List of persons
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
     * query the database for a person based on id
     * @param id id of the Person
     * @return List of Person
     */
    public Person findPersonWithId(String id) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Person p WHERE p.id = ?1", Person.class).setParameter(1, id).getSingleResult();
    }

    /**
     * query the database for persons based on name
     * @param name name of the Person
     * @return List of Person
     */
    public List<Person> findPersonWithName(String name) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Person p WHERE p.name LIKE ?1", Person.class).setParameter(1, name).getResultList();
    }

    /**
     * query the database for persons with optional query Parameters
     * @param id id of the Person
     * @param name name of the Person
     * @return List of Person
     */
    public List<Person> findPersonWithAll(String id, String name, String username) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Person p WHERE p.id = ?1"
                        + "OR p.name LIKE ?2"
                        + "OR p.username LIKE ?3", Person.class)
                .setParameter(1, id)
                .setParameter(2, name)
                .setParameter(3, username)
                .getResultList();
    }

    /**
     * query the database for persons with Company
     * @param name Company Name
     * @param catchPhrase catchphrase of the Company
     * @param bs bs of the Company
     * @return List of Person
     */
    public List<Person> findPersonWithCompany(String name, String catchPhrase, String bs) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Person p WHERE p.company.name = ?1"
                        + "OR p.company.catchPhrase LIKE ?2"
                        + "OR p.company.bs LIKE ?3", Person.class)
                .setParameter(1, name)
                .setParameter(2, catchPhrase)
                .setParameter(3, bs)
                .getResultList();
    }

    /**
     * query the database for persons with Address
     * @param street street of Address
     * @param suite suite of Address
     * @param city city of Address
     * @param zipcode zipcode of Address
     * @return List of Person
     */
    public List<Person> findPersonWithAddress(String street, String suite, String city, String zipcode) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Person p WHERE p.address.street = ?1"
                        + "OR p.address.suite LIKE ?2"
                        + "OR p.address.city LIKE ?3"
                        + "OR p.address.zipcode LIKE ?4", Person.class)
                .setParameter(1, street)
                .setParameter(2, suite)
                .setParameter(3, city)
                .setParameter(4, zipcode)
                .getResultList();
    }

    /**
     * query the database for persons within range of ids
     * @param start id of the Person
     * @param end name of the Person
     * @return List of Person
     */
    public List<Person> findPersonsWithIdRange(String start, String end) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Person p WHERE p.id BETWEEN ?1 AND ?2", Person.class)
                .setParameter(1, start)
                .setParameter(2, end)
                .getResultList();
    }
}
