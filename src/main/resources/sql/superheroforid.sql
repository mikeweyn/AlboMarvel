SELECT id, name, description, last_sync
FROM SUPERHERO
WHERE id IN (:id);