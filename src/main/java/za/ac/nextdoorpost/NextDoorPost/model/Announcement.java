package za.ac.nextdoorpost.NextDoorPost.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100)
    private String title;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Description is required")
    @Size(max = 1000)
    private String description;

    private String submittedBy;
    private String date;
    private boolean approved = false;

    // 🔹 New field for the image path or URL
    private String imagePath;

    // =========================
    // Constructors
    // =========================

    // Default constructor
    public Announcement() {}

    // Parameterized constructor (without ID)
    public Announcement(String title, String category, String description, String submittedBy, String date, boolean approved, String imagePath) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.submittedBy = submittedBy;
        this.date = date;
        this.approved = approved;
        this.imagePath = imagePath;
    }

    // Parameterized constructor (with ID)
    public Announcement(Long id, String title, String category, String description, String submittedBy, String date, boolean approved, String imagePath) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.submittedBy = submittedBy;
        this.date = date;
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

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(String submittedBy) { this.submittedBy = submittedBy; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    // =========================
    // toString()
    // =========================

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", submittedBy='" + submittedBy + '\'' +
                ", date='" + date + '\'' +
                ", approved=" + approved +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
