package app.service;

import app.model.Feedback;
import app.model.Reaction;
import app.model.ReactionName;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class HibernateFeedbackServiceImpl implements FeedbackService {
	private static SessionFactory sessionFactory;
	private static EntityManager entityManager;

	static {
		try {
			sessionFactory = new Configuration()
					.configure("hibernate.conf.xml")
					.buildSessionFactory();
			System.out.println(sessionFactory);
			entityManager = sessionFactory.createEntityManager();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Feedback findById(Long id) {
		return entityManager.find(Feedback.class, id);
	}

	@Override
	public void reactions(String reac, Feedback feedback) {
		Reaction reaction = new Reaction();
		reaction.setFeedback(feedback);
		reaction.setReaction(ReactionName.valueOf(reac));

		entityManager.getTransaction().begin();
		entityManager.persist(reaction);

		feedback.getReaction().add(reaction);
		entityManager.merge(feedback);

		entityManager.getTransaction().commit();
	}

	@Override
	public void save(Feedback feedback) {
		entityManager.getTransaction().begin();
		entityManager.persist(feedback);

		entityManager.getTransaction().commit();
	}

	@Override
	public List<Feedback> feedbackToday() {
		LocalDate localDate    = LocalDate.now();

		LocalDateTime today    = localDate.atStartOfDay();
		LocalDateTime tomorrow = today.plusDays(1);

		Timestamp timestamp_today    = Timestamp.valueOf(today);
		Timestamp timestamp_tomorrow = Timestamp.valueOf(tomorrow);

		List<Feedback> list = null;

		list = entityManager.createQuery("SELECT c FROM Feedback c WHERE c.created_at >= :today and c.created_at < :tomorrow")
				.setParameter("today", timestamp_today)
				.setParameter("tomorrow", timestamp_tomorrow)
				.getResultList();

		return list;
	}
}
