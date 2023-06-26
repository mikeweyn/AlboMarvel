UPDATE SUPERHERO SET last_sync = now()
WHERE id IN (:id);
