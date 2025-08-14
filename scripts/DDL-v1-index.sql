-- Indexes for label table
CREATE INDEX label_system_user_idx ON label USING btree (system_user_id);

-- Indexes for note table
CREATE INDEX note_system_user_idx ON note USING btree (system_user_id);

-- Indexes for label_note table
CREATE INDEX label_note_idx ON label_note USING btree (id_label, id_note);
