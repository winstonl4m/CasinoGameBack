-- Optional for local dev: clear existing rows so re-runs don't duplicate
-- DELETE FROM games;

INSERT INTO games (name, chance_of_winning, winning_multiplier, max_bet, min_bet) VALUES
  (  'Lucky 7 Slots',           0.45, 2.50, 100, 1),
  (  'Blackjack Classic',       0.49, 2.00, 200, 5),
  (  'Roulette Royale',         0.47, 3.00, 150, 2),
  (  'Pirate''s Treasure Wheel',0.35, 5.00,  50, 1),
  (  'Dragon Dice Duel',        0.42, 2.80,  80, 2),
  (  'Fortune Spin',            0.40, 4.00, 120, 3),
  (  'Gold Rush Poker',         0.38, 3.50, 200, 5),
  (  'Mystic Keno',             0.33, 6.00,  60, 1),
  (  'Spin of the Pharaoh',     0.37, 4.50, 100, 2),
  ( 'Jungle Jackpot',          0.43, 2.70,  90, 1),
  ( 'Viking Plunder',          0.41, 3.80, 110, 2),
  ( 'Neon Nights',             0.46, 2.30, 150, 3),
  ( 'Crystal Caverns',         0.39, 4.20,  70, 1),
  ( 'Cyber Spin City',         0.44, 2.60, 130, 2),
  ( 'Aurora Baccarat',         0.48, 2.10, 250,10),
  ( 'Starlight Scratchers',    0.30, 7.00,  40, 1);
