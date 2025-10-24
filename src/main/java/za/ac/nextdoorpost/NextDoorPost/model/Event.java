package za.ac.nextdoorpost.NextDoorPost.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Date is required")
    private String date;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Description is required")
    private String description;

    private String submittedBy;
    private boolean approved = false;

    // 🔹 New field for event image
    private String imagePath;

    // =========================
    // Constructors
    // =========================

    // Default constructor
    public Event() {}

    // Parameterized constructor (without ID)
    public Event(String title, String date, String location, String description, String submittedBy, boolean approved, String imagePath) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
        this.submittedBy = submittedBy;
        this.approved = approved;
        this.imagePath = imagePath;
    }

    // Parameterized constructor (with ID)
    public Event(Long id, String title, String date, String location, String description, String submittedBy, boolean approved, String imagePath) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
        this.submittedBy = submittedBy;
        this.approved = approved;
        this.imagePath = imagePath;
    }

    // =========================
    // Getters and Setters
    // =========================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(String submittedBy) { this.submittedBy = submittedBy; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    // =========================
    // toString()
    // =========================

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", submittedBy='" + submittedBy + '\'' +
                ", approved=" + approved +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
