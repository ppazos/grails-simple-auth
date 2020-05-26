
withConfig(configuration) {
    inline(phase: 'CONVERSION') { source, context, classNode ->
        classNode.putNodeMetaData('projectVersion', '0.4.1')
        classNode.putNodeMetaData('projectName', 'grails-simple-auth')
        classNode.putNodeMetaData('isPlugin', 'true')
    }
}
