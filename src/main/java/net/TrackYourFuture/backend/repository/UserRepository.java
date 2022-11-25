package net.TrackYourFuture.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.TrackYourFuture.backend.model.Profile;

@Repository
public interface UserRepository extends JpaRepository<Profile,Long>{

}