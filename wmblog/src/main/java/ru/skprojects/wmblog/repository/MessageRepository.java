package ru.skprojects.wmblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skprojects.wmblog.model.Message;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Override
    List<Message> findAll();

    @Override
    Optional<Message> findById(Integer integer);

    @Override
    <S extends Message> S save(S s);
}
