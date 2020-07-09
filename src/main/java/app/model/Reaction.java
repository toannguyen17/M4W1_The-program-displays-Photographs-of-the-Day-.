package app.model;

import javax.persistence.*;

@Entity
@Table
public class Reaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ReactionName reaction;

	@ManyToOne
	private Feedback feedback;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReactionName getReaction() {
		return reaction;
	}

	public void setReaction(ReactionName reaction) {
		this.reaction = reaction;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}
}
