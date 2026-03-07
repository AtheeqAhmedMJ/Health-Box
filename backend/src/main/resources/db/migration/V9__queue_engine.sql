-- =====================================
-- QUEUE ENGINE
-- =====================================

ALTER TABLE appointments
ADD COLUMN IF NOT EXISTS queue_number INT;

ALTER TABLE appointments
ADD COLUMN IF NOT EXISTS is_walk_in BOOLEAN DEFAULT FALSE;

ALTER TABLE appointments
ADD COLUMN IF NOT EXISTS check_in_time TIMESTAMP;

CREATE INDEX IF NOT EXISTS idx_queue_lookup
ON appointments(doctor_id, date, queue_number);