package com.jsp.book.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.book.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> // Spring automatically connects this file with database , hence momvue is added
{
	// this is in built spring data JPA query
	boolean existsByNameAndReleaseDate(String name, LocalDate releaseDate); 
}
