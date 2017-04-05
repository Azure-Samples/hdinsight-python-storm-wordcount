import storm
# Counter is a nice way to count things,
# but it is a Python 2.7 thing
from collections import Counter

class CountBolt(storm.BasicBolt):
    # Initialize this instance
    def initialize(self, conf, context):
        self._conf = conf
        self._context = context
        # Create a new counter for this instance
        self._counter = Counter()
        storm.logInfo("Counter bolt instance starting...")

    def process(self, tup):
        # Get the word from the inbound tuple
        word = tup.values[0]
        # Increment the counter
        self._counter[word] +=1
        count = self._counter[word]
        storm.logInfo("Emitting %s:%s" % (word, count))
        # Emit the word and count
        storm.emit([word, count])

# Start the bolt when it's invoked
CountBolt().run()
