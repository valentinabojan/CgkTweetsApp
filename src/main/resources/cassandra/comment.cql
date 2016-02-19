use tweets;

create TABLE if not exists comment (
    id varchar,
    post_id varchar,
    body varchar,
    author varchar,
    date timestamp,
    PRIMARY KEY(post_id, date)
);

insert into comment (id, post_id, body, author, date) values(
  '1',
  '1',
  'first comment',
  'andrew',
  '2013-03-11'
  );

insert into comment (id, post_id, body, author, date) values(
  '2',
  '1',
  'second comment',
  'maria',
  '2013-03-12'
  );

insert into comment (id, post_id, body, author, date) values(
  '3',
  '1',
  '3rd comment',
  'john',
  '2013-03-13'
  );

insert into comment (id, post_id, body, author, date) values(
  '4',
  '1',
  '4th comment',
  'boby',
  '2013-03-14'
  );

insert into comment (id, post_id, body, author, date) values(
  '5',
  '1',
  '5th comment',
  'boby 55',
  '2013-03-15'
  );

insert into comment (id, post_id, body, author, date) values(
  '6',
  '1',
  '6th comment',
  'boby 64',
  '2013-03-16'
  );