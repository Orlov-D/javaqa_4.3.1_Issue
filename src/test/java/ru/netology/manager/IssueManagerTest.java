package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class IssueManagerTest {
    private IssueRepository repository = new IssueRepository();
    private IssueManager manager = new IssueManager(repository);

    private Issue b0 = new Issue(0, new HashSet<>(Arrays.asList("constructor", "bublik", "ezik")), "Issue 0",
            new HashSet<>(Arrays.asList("component: Kotlin", "component: Jupiter", "status: New")), true,
            2021_02_21, "orl");
    private Issue b1 = new Issue(1, new HashSet<>(Collections.singletonList("denis")), "Issue 1",
            new HashSet<>(Arrays.asList("component: Groovy", "component: Jupiter", "status: New", "theme: build")),
            false, 2021_03_21, "ez");
    private Issue b2 = new Issue(2, new HashSet<>(Arrays.asList("vasya", "kotya")), "Issue 2",
            new HashSet<>(Arrays.asList("component: Vintage", "component: Jupiter", "status: Invalid")), true,
            2021_02_27, "grek");
    private Issue b3 = new Issue(3, new HashSet<>(), "Issue 3", new HashSet<>(), true, 2021_02_10, "orl");

    @BeforeEach
    void setUp() {
        manager.add(b0);
        manager.add(b1);
        manager.add(b2);
    }

    @Test
    void addNew() {
        manager.add(b3);
        assertEquals(Arrays.asList(b0, b1, b2, b3), manager.findAll());
    }

    @Test
    void findOpened() {
        assertEquals(Arrays.asList(b0, b2), manager.findOpened());
    }

    @Test
    void findClosed() {
        assertEquals(Collections.singletonList(b1), manager.findClosed());
    }

    @Test
    void filterByAuthor() {
        manager.add(b3);
        manager.add(b0);

        assertEquals(Arrays.asList(b0, b3, b0), manager.filterByAuthor("orL"));
    }

    @Test
    void filterByLabel() {
        manager.add(b3);
        manager.add(b0);

        assertEquals(Arrays.asList(b0, b0), manager.filterByLabel("component: Kotlin"));
    }

    @Test
    void filterByAssignee() {
        manager.add(b3);
        manager.add(b0);

        assertEquals(Collections.singletonList(b2), manager.filterByAssignee("vasya"));
    }

    @Test
    void sortNewest() {
        manager.add(b3);
        manager.add(b0);
        assertEquals(Arrays.asList(b3, b0, b0, b2, b1), manager.sortNewest());
    }

    @Test
    void sortOldest() {
        manager.add(b3);
        manager.add(b0);
        manager.sortOldest();
        assertEquals(Arrays.asList(b1, b2, b0, b0, b3), manager.sortOldest());
    }

    @Test
    void canCloseThis() {
        manager.closeIssue(2);
        assertFalse(manager.getIssueStatus(2));
    }

    @Test
    void cantCloseThis() {
        manager.closeIssue(1);
        assertFalse(manager.getIssueStatus(1));
    }

    @Test
    void canOpenThis() {
        manager.openIssue(1);
        assertTrue(manager.getIssueStatus(1));
    }

    @Test
    void cantOpenThis() {
        manager.openIssue(2);
        assertTrue(manager.getIssueStatus(2));
    }

}