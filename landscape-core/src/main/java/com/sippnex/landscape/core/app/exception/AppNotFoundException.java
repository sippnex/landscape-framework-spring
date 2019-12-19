package com.sippnex.landscape.core.app.exception;

import org.springframework.data.annotation.Immutable;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

@Immutable
public class AppNotFoundException extends AbstractThrowableProblem {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public AppNotFoundException(String appId) {
        super(URI.create(""), "App Not found", Status.NOT_FOUND, String.format("App %s not found", appId));
    }

}
