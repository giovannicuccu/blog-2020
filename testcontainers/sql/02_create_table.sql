create table diag.diagnostic_events(
	id serial not null,
	event_type varchar(30) not null,
	name varchar(120) not null,
	result varchar(8),
	elapsed_millis integer,
	start_ts timestamp not null,
	PRIMARY KEY(id)
);

grant all on diag.diagnostic_events to diag;
grant all on diag.diagnostic_events_id_seq to diag;
GRANT ALL ON SCHEMA diag to diag;