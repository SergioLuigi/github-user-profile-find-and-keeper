package br.com.sergioluigi.githubuserprofilefindandkeeper.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GitHubUserProfileError {

    USERNAME_REQUIRED(HttpStatus.BAD_REQUEST,"Username required"),
    USER_PROFILE_REQUIRED(HttpStatus.UNPROCESSABLE_ENTITY,"User profile required"),
    USER_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND,"User profile not found"),
    USER_PROFILE_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"Something unexpected happened. Please contact Support");

    private final HttpStatus httpStatus;

    private final String reason;
}
