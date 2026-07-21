import java.util.ArrayList;
import java.util.List;

public class JobProcessor {

    public void processActiveJobs(Company company, int milliseconds, DisplayManager displayManager) {
        List<Job> jobsToMove = new ArrayList<>();

        for (Job j : company.getActiveJobs()) {
            if (canProcessJob(j)) {
                processJob(j, company, milliseconds, displayManager, jobsToMove);
            }
        }

        moveCompletedJobs(company, jobsToMove);
    }

    private boolean canProcessJob(Job job) {
        return !job.getAssignedEmployees().isEmpty() && !job.getAssignedMachines().isEmpty();
    }

    private void processJob(Job job, Company company, int milliseconds,
                            DisplayManager displayManager, List<Job> jobsToMove) {
        for (Employee e : job.getAssignedEmployees()) {
            job.work(e.getExperience(), milliseconds, company.getMsPerDay());
        }

        job.updateElapsedDaysFromGlobal(company.getTotalMilliseconds(), company.getMsPerDay());
        displayManager.displayJobProgress(job);

        if (job.isCompleted()) {
            jobsToMove.add(job);
        }
    }

    private void moveCompletedJobs(Company company, List<Job> jobsToMove) {
        for (Job j : jobsToMove) {
            company.moveCompletedJob(j);
        }
    }
}