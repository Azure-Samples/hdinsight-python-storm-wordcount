import storm

class SplitBolt(storm.BasicBolt):
    # There's nothing to initialize here,
    # since this is just a split and emit
    # Initialize this instance
    def initialize(self, conf, context):
        self._conf = conf
        self._context = context
        storm.logInfo("Split bolt instance starting...")

    def process(self, tup):
        # Split the inbound sentence at spaces
        words = tup.values[0].split(" ")
        # Loop over words and emit
        for word in words:
          storm.logInfo("Emitting %s" % word)
          storm.emit([word])

# Start the bolt when it's invoked
SplitBolt().run()
