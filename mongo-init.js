db.createUser(
        {
            user: "farid",
            pwd: "ff",
            roles: [
                {
                    role: "readWrite",
                    db: "fardb"
                }
            ]
        }
);
