package app.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   id;
	private String author;
	private String comment;
	private int    rate;
	private Timestamp created_at;

	@OneToMany
	private List<Reaction> reaction;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public List<Reaction> getReaction() {
		return reaction;
	}

	public void setReaction(List<Reaction> reaction) {
		this.reaction = reaction;
	}

	public int checkReaction(String reac) {
		int like = 0;
		if (reaction != null){
			List<Reaction> result = reaction.stream()
					.filter(check -> ReactionName.valueOf(reac) == check.getReaction())
					.collect(Collectors.toList());
			like = result.size();
		}
		return like;
	}

	@Override
	public String toString() {
		return "Feedback{" +
				"id=" + id +
				", author='" + author + '\'' +
				", comment='" + comment + '\'' +
				", rate=" + rate +
				", created_at=" + created_at +
				", reaction=" + reaction +
				'}';
	}
}
