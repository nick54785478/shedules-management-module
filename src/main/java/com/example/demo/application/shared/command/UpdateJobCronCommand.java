package com.example.demo.application.shared.command;

public record UpdateJobCronCommand(String name, String group, String newCron) {
}