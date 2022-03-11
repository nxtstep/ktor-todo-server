package io.supersimple.todo.repositories.db

import io.supersimple.db.SQLDatabase

class NotesDatabase(db: SQLDatabase) : SQLDatabase by db
