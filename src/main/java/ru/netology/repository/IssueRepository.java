package ru.netology.repository;

import ru.netology.domain.Issue;

import java.util.*;

public class IssueRepository {
    private List<Issue> issues = new ArrayList<>();

    public void addNew(Issue issue) {
        issues.add(issue);
    }

    public List<Issue> findAll() {
        return issues;
    }

    public List<Issue> findOpened() {
        List<Issue> tmp = new ArrayList<>();
        for (Issue issue : issues) {
            if (issue.isOpen()) {
                tmp.add(issue);
            }
        }
        return tmp;
    }

    public List<Issue> findClosed() {
        List<Issue> tmp = new ArrayList<>();
        for (Issue issue : issues) {
            if (!issue.isOpen()) {
                tmp.add(issue);
            }
        }
        return tmp;
    }

    //где то тут начинаешь задумываться о предикатах... или кого-там нам показывали, чтобы заменить одинаковые методы.
    public List<Issue> filterByAuthor(String author) {
        List<Issue> tmp = new ArrayList<>();
        for (Issue issue : issues) {
            if (issue.getAuthor().equalsIgnoreCase(author)) {
                tmp.add(issue);
            }
        }
        return tmp;
    }

    public List<Issue> filterByLabel(String label) {
        List<Issue> tmp = new ArrayList<>();
        for (Issue issue : issues) {
            if (issue.getLabel().contains(label)) {
                tmp.add(issue);
            }
        }
        return tmp;
    }

    public List<Issue> filterByAssignee(String assignee) {
        List<Issue> tmp = new ArrayList<>();
        for (Issue issue : issues) {
            if (issue.getAssignee().contains(assignee)) {
                tmp.add(issue);
            }
        }
        return tmp;
    }

    public List<Issue> sortNewest() {
        issues.sort(Comparator.comparingInt(Issue::getDate));
        return issues;
    }

    public List<Issue> sortOldest() {
        issues.sort((a, b) -> b.getDate() - a.getDate());
        return issues;
    }

    public void openIssue(int id) {
        boolean flag = false;
        for (Issue issue : issues) {
            if (issue.getId() == id && !issue.isOpen()) {
                issue.setOpen(true);
                flag = true;
            }
        }
        if (!flag) {
            System.out.printf("No issue with id = %s or it's opened\n", id);
        }

    }

    public void closeIssue(int id) {
        boolean flag = false;
        for (Issue issue : issues) {
            if (issue.getId() == id && issue.isOpen()) {
                issue.setOpen(false);
                flag = true;
            }
        }
        if (!flag) {
            System.out.printf("No issue with id = %s or it's closed\n", id);
        }
    }


}
