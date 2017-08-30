package com.yak.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Herd {

	private Set<Yak> herd;

	public class Yak {

		private String name;
		private float age;
		@JsonProperty(value = "age-last-shaved")
		private float ageLastShaved;

		public Yak() {

		}
		public Yak(String name, float age, float ageLastShaved) {
			super();
			this.name = name;
			this.age = age;
			this.ageLastShaved = ageLastShaved;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public float getAge() {
			return age;
		}

		public void setAge(float age) {
			this.age = age;
		}

		public float getAgeLastShaved() {
			return ageLastShaved;
		}

		public void setAgeLastShaved(float ageLastShaved) {
			this.ageLastShaved = ageLastShaved;
		}
		private Herd getOuterType() {
			return Herd.this;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Yak other = (Yak) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	}

	public Set<Yak> getHerd() {
		return herd;
	}

	public void setHerd(Set<Yak> herd) {
		this.herd = herd;
	}

}
