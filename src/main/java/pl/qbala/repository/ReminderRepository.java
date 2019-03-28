package pl.qbala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.qbala.entity.Reminder;

import java.time.LocalDate;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findAllByDate(LocalDate date);
}
