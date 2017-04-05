import storm
import random
# Define some sentences
SENTENCES = """
the cow jumped over the moon
an apple a day keeps the doctor away
four score and seven years ago
snow white and the seven dwarfs
i am at two with nature
""".strip().split('\n')

class SentenceSpout(storm.Spout):
    # Not much to do here for such a basic spout
    def initialize(self, conf, context):
        self._conf = conf
        self._context = context

        storm.logInfo("Spout instance starting...")

    # Process the next tuple
    def nextTuple(self):
        # Emit a random sentence
        sentence = random.choice(SENTENCES)
        storm.logInfo("Emiting %s" % sentence)
        storm.emit([sentence])

# Start the spout when it's invoked
SentenceSpout().run()
