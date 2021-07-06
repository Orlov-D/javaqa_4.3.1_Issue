package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class IssueRepositoryTest {
    private IssueRepository repository = new IssueRepository();

    private Issue b0 = new Issue(0, new HashSet<>(), "Issue 0", new HashSet<>(), true, 2021_02_21, "orl");
    private Issue b1 = new Issue(1, new HashSet<>(), "Issue 1", new HashSet<>(), false, 2021_03_21, "ez");
    private Issue b2 = new Issue(2, new HashSet<>(), "Issue 2", new HashSet<>(), true, 2021_02_27, "grek");
    private Issue b3 = new Issue(3, new HashSet<>(), "Issue 3", new HashSet<>(), true, 2021_02_10, "orl");

//    как сразу запихнуть в сет данные?... заменить "new HashSet<>()" на то что в setUp
//    видимо никак. сразу коллекцию... или hashMap...

    @BeforeEach
    void setUp() {
        b0.addAssignee("constructor");
        b0.addAssignee("bublik");
        b0.addAssignee("constructor");
        b0.addAssignee("ezik");
        b0.addLabel("component: Kotlin");
        b0.addLabel("component: Jupiter");
        b0.addLabel("status: New");

        b1.addAssignee("denis");
        b1.addLabel("component: Groovy");
        b1.addLabel("component: Jupiter");
        b1.addLabel("status: New");
        b1.addLabel("theme: build");

        b2.addAssignee("vasya");
        b2.addAssignee("kotya");
        b2.addLabel("component: Vintage");
        b2.addLabel("component: Jupiter");
        b2.addLabel("status: Invalid");

        repository.addNew(b0);
        repository.addNew(b1);
        repository.addNew(b2);
    }

    @Test
    void addNew() {
        repository.addNew(b3);
//        имеет ли смысл этот тест, если перед каждым уже добавлялись три элемента?...
        assertEquals(Arrays.asList(b0, b1, b2, b3), repository.findAll());
    }

    @Test
    void findOpened() {
        assertEquals(Arrays.asList(b0, b2), repository.findOpened());
    }

    @Test
    void findClosed() {
        assertEquals(Collections.singletonList(b1), repository.findClosed());
    }

    @Test
    void filterByAuthor() {
        repository.addNew(b3);
        repository.addNew(b0);

        assertEquals(Arrays.asList(b0, b3, b0), repository.filterByAuthor("orL"));
    }

    @Test
    void filterByLabel() {
        repository.addNew(b3);
        repository.addNew(b0);

        assertEquals(Arrays.asList(b0, b0), repository.filterByLabel("component: Kotlin"));
    }

    @Test
    void filterByAssignee() {
        repository.addNew(b3);
        repository.addNew(b0);

        assertEquals(Collections.singletonList(b2), repository.filterByAssignee("vasya"));
    }

    @Test
    void sortNewest() {
        repository.addNew(b3);
        repository.addNew(b0);
        repository.sortNewest();
        assertEquals(Arrays.asList(b3, b0, b0, b2, b1), repository.sortNewest());
        repository.sortOldest();
    }

    @Test
    void sortOldest() {
        repository.addNew(b3);
        repository.addNew(b0);
        repository.sortOldest();
        assertEquals(Arrays.asList(b1, b2, b0, b0, b3), repository.sortOldest());
    }

    @Test
    void canCloseThis() {
        repository.closeIssue(2);
        assertEquals(false, repository.getIssueStatus(2));
    }

    @Test
    void cantCloseThis() {
        repository.closeIssue(2);
//        Ассерт на строчку делать?...аналогично в открытие открытого.
        assertEquals(false, repository.getIssueStatus(2));
    }

    @Test
    void canOpenThis() {
        repository.openIssue(2);
        assertEquals(true, repository.getIssueStatus(2));
    }

    @Test
    void cantOpenThis() {
        repository.openIssue(2);
        assertEquals(true, repository.getIssueStatus(2));
    }

}