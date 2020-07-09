package app.controller;

import app.model.Feedback;
import app.model.ReactionName;
import app.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {
	@Autowired
	FeedbackService feedbackService;

	@GetMapping("/")
	public String index(Model model){
		List<Feedback> list = feedbackService.feedbackToday();
		model.addAttribute("feedbacks", list);
		return "index";
	}

	@PostMapping("/")
	public String indexPost(@ModelAttribute Feedback feedback, Model model){
		Timestamp create_at = new Timestamp(new Date().getTime());
		feedback.setCreated_at(create_at);

		feedbackService.save(feedback);
		List<Feedback> list = feedbackService.feedbackToday();
		model.addAttribute("feedbacks", list);
		return "index";
	}

	@GetMapping("/reaction")
	public String reaction(@RequestParam String r, @RequestParam Long id,  Model model){
		Feedback feedback = feedbackService.findById(id);
		if (feedback != null){
			switch (r){
				case "Like":
					feedbackService.reactions("Like", feedback);
					break;

				case "Love":
					feedbackService.reactions("Love", feedback);
					break;

				case "Haha":
					feedbackService.reactions("Haha", feedback);
					break;

				case "Angry":
					feedbackService.reactions("Angry", feedback);
					break;

				case "Cry":
					feedbackService.reactions("Cry", feedback);
					break;
			}
		}

		return "redirect:/";
	}
}
