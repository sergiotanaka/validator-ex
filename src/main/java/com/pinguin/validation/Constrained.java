package com.pinguin.validation;

import java.util.Collection;

public interface Constrained {

	public abstract Collection<Constraint> getConstraints();

}