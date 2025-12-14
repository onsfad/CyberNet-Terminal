package com.austine.blog.repository.oauthrepository;

import com.austine.blog.model.auth.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthClientDetailsRepo extends JpaRepository<OauthClientDetails, Long> {
}
