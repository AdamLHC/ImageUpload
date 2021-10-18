# ImageUpload

A demonstration project for uploading and storing file, with membership system.

The project uses two databases:
- MariaDB: For storing member and sumbmission data.
- MongoDB: For storing binary file with GridFS.

API endpoints So far:

- `GET` /submission => For getting all submissions.
- `GET` /submission/owned => For getting submission owned by self (authentication needed)
- `POST` /submission  => For creating sumbmission and uploading files.

- `POST` /member => Registering and creating user.

- `GET` /file => for downloading file.