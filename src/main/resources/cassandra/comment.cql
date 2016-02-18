use tweets;

create TABLE comment (
    id varchar,
    post_id varchar,
    body varchar,
    author varchar,
    date timestamp,
    PRIMARY KEY(post_id, date)
);