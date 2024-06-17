package database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Iterator;
import java.util.List;

public class DataManager {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Fraunhofer_PU");
    public DataManager() {
    }

    private EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * persists a Person
     * @param person Person Entity
     */
    public void persistPerson(Person person) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * persists a List of Persons
     * @param personList List of Person Entities
     */
    public void persistPerson(List<Person> personList) {
        EntityManager em = createEntityManager();
        Iterator<Person> it = personList.iterator();
        em.getTransaction().begin();

        while (it.hasNext()) {
            em.persist(it.next());
        }
        em.getTransaction().commit();
        em.close();
    }

    /**
     *
     * @param id id of the Person
     * @return List of Person
     */
    public List<?> findPersonWithId(String id) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Person p WHERE p.id = ?1").setParameter(1, id).getResultList();
    }

    public List<?> findPersonWithName(String name) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Person p WHERE p.name = ?1").setParameter(1, name).getResultList();
    }
}
