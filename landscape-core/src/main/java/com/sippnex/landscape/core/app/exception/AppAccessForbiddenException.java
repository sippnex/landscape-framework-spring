package com.sippnex.landscape.core.app.exception;

import org.springframework.data.annotation.Immutable;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

@Immutable
public final class AppAccessForbiddenException extends AbstractThrowableProblem {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public AppAccessForbiddenException(Long appId) {
        super(URI.create(""), "App Access denied", Status.FORBIDDEN, String.format("Access to App %s has been denied", appId));
    }

}
