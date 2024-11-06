package ToDoList;
import java.time.LocalDateTime;
class Task {
    private String description;
    private boolean completed;
    private String priority;
    private String dueDate;
    private final LocalDateTime createdAt;

    public Task(String description) {
        this.description = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}