**Rest API:**<br />

#
**Test app:**<br />
`GET "/"` returns Hello World<br />

#
**Entities api:**<br />
`GET "/api/entities"`           returns the whole entities collection from database<br />
`GET "/api/entities/{id}"`      returns one entity by from database by **id**<br />
`POST "/api/entities"`          adds one entity to database<br />
`PUT "/api/entities"`           adds the whole entities collection to database<br />
`PUT "/api/entities/{id}"`      updates one entity by in database by **id**<br />
`PATCH "/api/entities"`         updates the whole entities collection in database<br />
`DELETE "/api/entities/{id}"`   deletes one entity by from database by **id**<br />
`DELETE "/api/entities"`        deletes the whole entities collection from database<br />

#
**Files api:**<br />
`GET "/api/files/preview/{id}"` shows file content from storage by **id**<br />
`GET "/api/files/{id}"`         downloads one file from storage by **id**<br />
`POST "/api/files"`             uploads one file to storage<br />
`POST "/api/files/multi"`       uploads files collection to storage<br />
`DELETE "/api/files/{id}"`      deletes one file from storage by **id**<br />
`DELETE "/api/files"`           deletes all files from storage<br />