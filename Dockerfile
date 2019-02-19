FROM postgres:11.2-alpine

ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_USER=postgres
ENV POSTGRES_DB=task_manager_db

EXPOSE 5432