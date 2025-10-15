-- Drop (for dev reloads)
--DROP TABLE IF EXISTS games;

-- Core table
CREATE TABLE IF NOT EXISTS games (
  game_id                  INT AUTO_INCREMENT PRIMARY KEY,
  name                VARCHAR(100) NOT NULL,
  chance_of_winning   DECIMAL(3,2) NOT NULL,   -- 0.00 to 1.00
  winning_multiplier  DECIMAL(4,2) NOT NULL,   -- e.g., 2.50, 6.00
  max_bet             INT NOT NULL,
  min_bet             INT NOT NULL,

  -- Constraints
  CONSTRAINT chk_chance_range
    CHECK (chance_of_winning >= 0.00 AND chance_of_winning <= 1.00),

  CONSTRAINT chk_multiplier_positive
    CHECK (winning_multiplier > 0),

  CONSTRAINT chk_bet_positive
    CHECK (min_bet > 0 AND max_bet > 0),

  CONSTRAINT chk_bet_order
    CHECK (min_bet <= max_bet)
);

CREATE TABLE IF NOT EXISTS players
(
    player_id       INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255)                          NOT NULL,
    username         VARCHAR(255)                          NOT NULL UNIQUE,
    password_hash VARCHAR(500)                          NOT NULL,
    balance        INT                                   NOT NULL,
    created_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    created_by    VARCHAR(20) DEFAULT 'SYSTEM',
    updated_at    TIMESTAMP   DEFAULT NULL,
    updated_by    VARCHAR(20) DEFAULT NULL
);



