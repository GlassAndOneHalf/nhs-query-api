NHS Query API
====

Introduction
---
This application exposes a single REST endpoint that allows users to 
send natural-language queries

Running the API
---
1. Ensure the input directory is correct in `application.conf`. This directory
should contain the files from the [NHS Website Scraper](https://github.com/GlassAndOneHalf/nhs-website-scraper)

2. Ensure you have [SBT]() installed.

3. Navigate to the project in your favourite terminal application and enter `sbt run`

Using the API
---
To send a query to the API, send a POST request to `http://host:port/query/symptoms`
containing a JSON body. For example:
```
{
    "query": "what are the symptoms of cancer?"
}
```

If the API can not find a match for the query, a 404 response will be returned.

If a match is found, the server will return a URL pointing to a relevant NHS
webpage. For example:

```
{
    "url": "http://www.nhs.uk/conditions/cancer?nobeta=true"
}
```

To stop the application, simply perform an interrupt with `Ctrl-C`.

Potential Improvements
---
Due to time constraints, there are some improvements that could be made to this application.

- Parameterise the query builder function in LuceneService to enhance flexibility and
testability. E.g.

  `def executeQuery(userQuery: SymptomQuery)(implicit builder: QueryBuilder): Option[SymptomResponse]`

- Add end-to-end tests to cover the entire user journey.
- Add tests for part of the application that use Lucene. I.e. by using lucene-test-framework.
