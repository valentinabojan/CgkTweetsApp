use tweets;

create TABLE if not exists tweet(
    id varchar,
    title varchar,
    body varchar,
    author varchar,
    users_who_liked set<varchar>,
    users_who_disliked set<varchar>,
    date timestamp,
    temp_key int,
    PRIMARY KEY(temp_key, date)
);

create INDEX if not exists idx ON tweet (id);

insert into tweet (temp_key, date, title, body, author, users_who_liked, users_who_disliked, id) values(
  1,
  '2016-01-31 20:00:00+0000',
  'Martians to use MongoDB',
  'Representatives from the planet Mars announced today that the planet would adopt MongoDB as a planetary standard. Head Martian Flipblip said that MongoDB was the perfect tool to store the diversity of life that exists on Mars.',
  'andrew',
  {'andrew'},
  {},
  '1'
  );

insert into tweet (temp_key, date, title, body, author, users_who_liked, users_who_disliked, id) values(
  1,
  '2014-05-12',
  'Declaration of Independence',
  'When in the Course of human events, it becomes necessary for one people to dissolve the political bands which have connected them with another, and to assume among the powers of the earth, the separate and equal station to which the Laws of Nature and of Nature''s God entitle them, a decent respect to the opinions of mankind requires that they should declare the causes which impel them to the separation.\nWe hold these truths to be self-evident, that all men are created equal, that they are endowed by their Creator with certain unalienable Rights, that among these are Life, Liberty and the pursuit of Happiness.\n\tThat to secure these rights, Governments are instituted among Men, deriving their just powers from the consent of the governed, That whenever any Form of Government becomes destructive of these ends, it is the Right of the People to alter or to abolish it, and to institute new Government, laying its foundation on such principles and organizing its powers in such form, as to them shall seem most likely to effect their Safety and Happiness. Prudence, indeed, will dictate that Governments long established should not be changed for light and transient causes; and accordingly all experience hath shewn, that mankind are more disposed to suffer, while evils are sufferable, than to right themselves by abolishing the forms to which they are accustomed. But when a long train of abuses and usurpations, pursuing invariably the same Object evinces a design to reduce them under absolute Despotism, it is their right, it is their duty, to throw off such Government, and to provide new Guards for their future security.\"\n',
  'Maria Jones',
  {},
  {},
  '2'
  );

insert into tweet (temp_key, date, title, body, author, users_who_liked, users_who_disliked, id) values(
  1,
  '2011-06-12',
  'Bill of Rights',
  'Amendment I\nCongress shall make no law respecting an establishment of religion, or prohibiting the free exercise thereof; or abridging the freedom of speech, or of the press; or the right of the people peaceably to assemble, and to petition the Government for a redress of grievances.\n\nAmendment II\nA well regulated Militia, being necessary to the security of a free State, the right of the people to keep and bear Arms, shall not be infringed.\n\nAmendment III\nNo Soldier shall, in time of peace be quartered in any house, without the consent of the Owner, nor in time of war, but in a manner to be prescribed by law.\n\nAmendment IV\nThe right of the people to be secure in their persons, houses, papers, and effects, against unreasonable searches and seizures, shall not be violated, and no Warrants shall issue, but upon probable cause, supported by Oath or affirmation, and particularly describing the place to be searched, and the persons or things to be seized.\n\nAmendment V\nNo person shall be held to answer for a capital, or otherwise infamous crime, unless on a presentment or indictment of a Grand Jury, except in cases arising in the land or naval forces, or in the Militia, when in actual service in time of War or public danger; nor shall any person be subject for the same offence to be twice put in jeopardy of life or limb; nor shall be compelled in any criminal case to be a witness against himself, nor be deprived of life, liberty, or property, without due process of law; nor shall private property be taken for public use, without just compensation.\n\nAmendment VI\nIn all criminal prosecutions, the accused shall enjoy the right to a speedy and public trial, by an impartial jury of the State and district wherein the crime shall have been committed, which district shall have been previously ascertained by law, and to be informed of the nature and cause of the accusation; to be confronted with the witnesses against him; to have compulsory process for obtaining witnesses in his favor, and to have the Assistance of Counsel for his defence.\n\nAmendment VII\nIn Suits at common law, where the value in controversy shall exceed twenty dollars, the right of trial by jury shall be preserved, and no fact tried by a jury, shall be otherwise re-examined in any Court of the United States, than according to the rules of the common law.\n\nAmendment VIII\nExcessive bail shall not be required, nor excessive fines imposed, nor cruel and unusual punishments inflicted.\n\nAmendment IX\nThe enumeration in the Constitution, of certain rights, shall not be construed to deny or disparage others retained by the people.\n\nAmendment X\nThe powers not delegated to the United States by the Constitution, nor prohibited by it to the States, are reserved to the States respectively, or to the people.\"\n\n',
  'Bill Smith',
  {'Maria Jones', 'andrew'},
  {},
  '3'
  );

insert into tweet (temp_key, date, title, body, author, users_who_liked, users_who_disliked, id) values(
  1,
  '2015-06-12',
  'Gettysburg Address',
  'Four score and seven years ago our fathers brought forth on this continent a new nation, conceived in liberty, and dedicated to the proposition that all men are created equal.<p>Now we are engaged in a great civil war, testing whether that nation, or any nation, so conceived and so dedicated, can long endure. We are met on a great battle-field of that war. We have come to dedicate a portion of that field, as a final resting place for those who here gave their lives that that nation might live. It is altogether fitting and proper that we should do this.<p>But, in a larger sense, we can not dedicate, we can not consecrate, we can not hallow this ground.',
  'Bill Smith',
  {},
  {},
  '4'
  );

insert into tweet (temp_key, date, title, body, author, users_who_liked, users_who_disliked, id) values(
  1,
  '2015-12-26',
  'Refactoring - book review',
  'It doesn''t matter if your IDE has refactoring capabilities built-in. You deserve yourself reading this one so you can refactor your code more effectively, but even more important: why and when you should (or shouldn''t) refactor.\n\nI''d practiced Object-Oriented programming for 10 years before reading this book, only to find out that I didn''t understand it even a little bit. This book was a real eye-opener.\n\nAnd while you''re reading through it, you''ll also learn about good OOD too!\n\nInstant classic!',
  'Oliver Queen',
  {'Maria Jones', 'andrew'},
  {},
  '5'
  );

insert into tweet (temp_key, date, title, body, author, users_who_liked, users_who_disliked, id) values(
  1,
  '2016-01-26',
  'Refactoring - a simple book review',
  'Like many other books of Martin Fowler, easy to read and easy to follow. Though a bit too simple to reflect the complicated nature of refactoring and how decision should be made (but the example in the first chapter is great). The language used in the book is Java, making it quite less practical for a user of dynamic languages like me.',
  'Felicity Smoak',
  {'Maria Jones'},
  {},
  '6'
  );