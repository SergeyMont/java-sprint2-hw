package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTaskTest {
    EpicTask ep1=new EpicTask("Сделать ремонт", "Выполнить ремонт до Нового " +
            "года", 3);
    SubTask sub1 = new SubTask("Отшпаклевать стены", "Шпаклевка и шлифовка", 4, 3);
    SubTask sub2 = new SubTask("Положить ламинат", "Подложка и ламинат", 5, 3);
    SubTask sub3 = new SubTask("Поклеить обои", "Обои с подбором", 6, 3);

    @Test
    void EpicShouldBeNewWithNoSubTask() {
        assertEquals(StatusTask.NEW,ep1.getStatus());
    }

    @Test
    void EpicShouldBeNewWithAllNewSubTask() {
        ep1.getSubTasks().add(sub1);
        ep1.getSubTasks().add(sub2);
        ep1.getSubTasks().add(sub3);
        assertEquals(StatusTask.NEW,ep1.getStatus());
    }

    @Test
    void EpicShouldBeDoneWithAllDoneSubTask() {
        sub1.setStatus(StatusTask.DONE);
        sub2.setStatus(StatusTask.DONE);
        sub3.setStatus(StatusTask.DONE);
        ep1.getSubTasks().add(sub1);
        ep1.getSubTasks().add(sub2);
        ep1.getSubTasks().add(sub3);
        assertEquals(StatusTask.DONE,ep1.getStatus());
    }

    @Test
    void EpicShouldBeInProgressWithNewAndDoneSubTask() {
        sub1.setStatus(StatusTask.NEW);
        sub2.setStatus(StatusTask.DONE);
        sub3.setStatus(StatusTask.DONE);
        ep1.getSubTasks().add(sub1);
        ep1.getSubTasks().add(sub2);
        ep1.getSubTasks().add(sub3);
        assertEquals(StatusTask.IN_PROGRESS,ep1.getStatus());
    }

    @Test
    void EpicShouldBeInProgressWithAllInProgressSubTask() {
        sub1.setStatus(StatusTask.IN_PROGRESS);
        sub2.setStatus(StatusTask.IN_PROGRESS);
        sub3.setStatus(StatusTask.IN_PROGRESS);
        ep1.getSubTasks().add(sub1);
        ep1.getSubTasks().add(sub2);
        ep1.getSubTasks().add(sub3);
        assertEquals(StatusTask.IN_PROGRESS,ep1.getStatus());
    }
}