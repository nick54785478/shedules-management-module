package com.example.demo.application.domain.schedule.aggregate.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record JobId(String value) {
}