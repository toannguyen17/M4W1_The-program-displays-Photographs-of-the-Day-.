package app.service;

import app.model.Feedback;

import java.sql.Timestamp;
import java.util.List;

public interface FeedbackService {
	Feedback findById(Long id);

	void reactions(String reaction, Feedback feedback);

	void save(Feedback feedback);

	List<Feedback> feedbackToday();
}
