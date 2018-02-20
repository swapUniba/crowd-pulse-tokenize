crowd-pulse-tokenize
====================

Generic Crowd Pulse message tokenizer.

You have to specify the configuration option "calculate" with one of the following values:
- all: to calculate the sentiment of all messages coming from the stream;
- new: to calculate the sentiment of the messages with no sentiment (property is null);

Example of usage:

```json
{
  "process": {
    "name": "tokenizer-tester",
    "logs": "/opt/crowd-pulse/logs"
  },
  "nodes": {
    "message-fetcher": {
      "plugin": "message-fetch",
      "config": {
        "db": "test"
      }
    },
    "tokenizer": {
      "plugin": "tokenizer-opennlp",
      "config": {
        "minChars": "4",
        "mentions": "true",
        "urls": "true",
        "hashtags": "false",
        "calculate": "new"
      }
    },
    "message-persister": {
      "plugin": "message-persist",
      "config": {
        "db": "test-sentiment"
      }
    }
  },
  "edges": {
    "message-fetcher": [
      "tokenizer"
    ],
    "tokenizer": [
      "message-persister"
    ]
  }
}
```