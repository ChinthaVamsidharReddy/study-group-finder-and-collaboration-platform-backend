-- Milestone 4: Session Management Tables
-- Create sessions table
CREATE TABLE IF NOT EXISTS sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    start_time DATETIME,
    end_time DATETIME,
    created_by BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reminder_options VARCHAR(100),
    is_poll BOOLEAN NOT NULL DEFAULT FALSE,
    confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (group_id) REFERENCES study_groups(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES Users(id) ON DELETE CASCADE,
    INDEX idx_group_id (group_id),
    INDEX idx_start_time (start_time),
    INDEX idx_confirmed (confirmed)
);

-- Create session_rsvps table
CREATE TABLE IF NOT EXISTS session_rsvps (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    response VARCHAR(10) NOT NULL CHECK (response IN ('yes', 'no', 'maybe')),
    timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    UNIQUE KEY uk_session_user (session_id, user_id),
    INDEX idx_session_id (session_id),
    INDEX idx_user_id (user_id)
);

-- Create session_votes table (for poll sessions)
CREATE TABLE IF NOT EXISTS session_votes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    voted_time_slot DATETIME NOT NULL,
    timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    UNIQUE KEY uk_session_user (session_id, user_id),
    INDEX idx_session_id (session_id),
    INDEX idx_user_id (user_id)
);

-- Create session_time_slots table (for poll sessions)
CREATE TABLE IF NOT EXISTS session_time_slots (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    vote_count INT NOT NULL DEFAULT 0,
    FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE,
    INDEX idx_session_id (session_id),
    INDEX idx_start_time (start_time)
);

-- Create session_reminders table (idempotent reminder tracking)
CREATE TABLE IF NOT EXISTS session_reminders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    reminder_offset INT NOT NULL COMMENT 'Minutes before session (30, 15, 14, etc.)',
    sent_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    UNIQUE KEY uk_session_user_offset (session_id, user_id, reminder_offset),
    INDEX idx_session_id (session_id),
    INDEX idx_user_id (user_id),
    INDEX idx_sent_at (sent_at)
);

